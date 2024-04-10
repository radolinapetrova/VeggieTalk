import {BrowserRouter, Route, Routes} from "react-router-dom";
import Homepage from "./Homepage";
import AuthPage from "./AuthPage";
import AccountPage from "./AccountPage";
import PostPage from "./PostPage";
import Layout from "./Layout";


export default function Navigation() {



    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Layout/>}>
                    <Route index element={<Homepage/>}/>
                    <Route path="post" element={<PostPage/>}/>
                    <Route path="login" element={<AuthPage/>}/>
                    <Route path="account" element={<AccountPage/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
    );
}