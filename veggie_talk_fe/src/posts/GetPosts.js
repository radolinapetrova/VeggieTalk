import axios from "axios";
import { useEffect, useState } from "react";
import ImageDisplay from '../pages/ImageDisplay';
import "./GetPosts.css"; // Import the CSS file

function GetPosts() {
    const [posts, setPosts] = useState([]);

    useEffect(() => {
        getPosts();
    }, []);

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
                    </div>
                ))}
            </div>
        );
    };

    function getPosts() {
        axios.get('http://localhost:8081/api/posts/page/0')
            .then(res => {
                setPosts(res.data);
            })
            .catch(err => {
                console.error('Error fetching posts:', err);
            });
    }

    return mapPosts();
}

export default GetPosts;
