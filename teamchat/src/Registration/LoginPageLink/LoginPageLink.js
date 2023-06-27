import { Link } from "react-router-dom";

function LoginPageLink() {
    return (
        <div className="middlePosition">
            <span className="psw ">Have an account?
                <Link to="/">Log in</Link>
            </span>
        </div>
    );
}

export default LoginPageLink;