import { Link, Outlet } from "react-router-dom";
import {useAuth} from "../auth/AuthProvider"

export default function Layout() {
    const {auth} = useAuth();

    return (
        <div className="main">
            <nav className="nav">
                <Link className="link" to="/">Home</Link>
                {auth ? (
                    <>
                        <Link className="link" to="/account">Account</Link>
                        <Link className="link" to="/post">Post</Link>
                    </>
                ) : (
                    <Link className="link" to="/login">Log in</Link>
                )}
            </nav>
            <Outlet />
        </div>
    );
}
