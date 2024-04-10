import {Link, Outlet} from "react-router-dom";

export default function Layout(){
    return (
        <div className="main">
            <nav className="nav">
                <Link className="link" to="/">Home</Link>
                <Link className="link" to="/login">Log in</Link>
                <Link className="link" to="/post">Post</Link>
            </nav>
            <Outlet/>
        </div>
    )
}