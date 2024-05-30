import axios from "axios";
import {useEffect, useState} from "react";

export default function GetPosts(){

    const [posts, setPosts] = useState([])

    useEffect(() => {
        console.log('useEffect triggered');
        setPosts([{ id: 1, description: 'Dummy Description', date: 'Dummy Date' }]);
        getPosts()
    }, []);


    const mapPosts = () => {

        console.log("herreeeeeee")

        return (
            <div>
                <p>Posts</p>
                {posts.map((post) => (
                    <div key={post.id}>
                        <p>{post.description}</p>
                        <p>{post.date}</p>
                    </div>
                ))}
            </div>
        );
    };


    function getPosts() {
        axios.get('http://localhost:8080/api/posts')
            .then(res => {
                console.log('Response Data:', res.data); // Log response data structure
                setPosts(res.data);
            })
            .catch(err => {
                console.error('Error fetching posts:', err);
            });
    }

    return (
        mapPosts()
    )

}