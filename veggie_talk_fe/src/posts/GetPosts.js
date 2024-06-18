import axios from "axios";
import { useEffect, useState } from "react";
import ImageDisplay from './ImageDisplay';
import "./GetPosts.css";

const ratingOptions = ["NO_STARS", "ONE_STAR", "TWO_STARS", "THREE_STARS", "FOUR_STARS", "FIVE_STARS"];

function GetPosts() {
    const [posts, setPosts] = useState([]);
    const [comments, setComments] = useState({}); // To store comments for each post
    const [newComment, setNewComment] = useState('');
    const [newRating, setNewRating] = useState('NO_STARS');

    useEffect(() => {
        getPosts();
    }, []);

    const getPosts = () => {
        axios.get('http://localhost:8081/api/posts/page/0')
            .then(res => {
                setPosts(res.data);
                // Fetch comments for each post after getting posts
                res.data.forEach(post => fetchComments(post.id));
            })
            .catch(err => {
                console.error('Error fetching posts:', err);
            });
    };

    const fetchComments = (postId) => {
        axios.get(`http://localhost:8082/api/comments/${postId}`)
            .then(res => {
                setComments(prev => ({ ...prev, [postId]: res.data }));
            })
            .catch(err => {
                if (err.response && err.response.status === 417) {
                    setComments(prev => ({ ...prev, [postId]: [] }));
                } else {
                    console.error('Error fetching comments:', err);
                }
            });
    };

    const handleAddComment = (postId) => {
        const comment = { text: newComment, rating: newRating };

        axios.post(`http://localhost:8081/api/comments/${postId}`, comment)
            .then(res => {
                setComments(prev => ({
                    ...prev,
                    [postId]: [...prev[postId], res.data]
                }));
                setNewComment('');
                setNewRating('NO_STARS');
            })
            .catch(err => {
                console.error('Error adding comment:', err);
            });
    };

    const mapPosts = () => {
        return (
            <div className="posts-container">
                <h2>Posts</h2>
                {posts.map((post) => (
                    <div key={post.id} className="post-card">
                        <p className="post-description">{post.description}</p>
                        <p className="post-date">{post.date}</p>
                        <div className="image-container">
                            <ImageDisplay fileNames={post.fileIds} />
                        </div>
                        <div className="comments">
                            {comments[post.id] && comments[post.id].length > 0 ? (
                                comments[post.id].map((comment, index) => (
                                    <div key={index} className="comment">
                                        <p>{comment.text}</p>
                                        <p className="rating">Rating: {comment.rating}</p>
                                    </div>
                                ))
                            ) : (
                                <p>No comments yet.</p>
                            )}
                        </div>
                        <div className="add-comment">
                            <textarea
                                value={newComment}
                                onChange={(e) => setNewComment(e.target.value)}
                                placeholder="Add a comment"
                            />
                            <select
                                value={newRating}
                                onChange={(e) => setNewRating(e.target.value)}
                            >
                                {ratingOptions.map((option, index) => (
                                    <option key={index} value={option}>
                                        {option.replace('_', ' ')}
                                    </option>
                                ))}
                            </select>
                            <button onClick={() => handleAddComment(post.id)}>Submit</button>
                        </div>
                    </div>
                ))}
            </div>
        );
    };

    return mapPosts();
}

export default GetPosts;
