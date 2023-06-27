import { Link } from "react-router-dom";

function LinkRegistration() {
    return (
        // <!--link to Registration page-->
        <div className="middlePosition">
            <span className="psw">Don't have an account?
                <Link to="/Registration">Sign up</Link>
            </span>
        </div>
    );
}

export default LinkRegistration;
