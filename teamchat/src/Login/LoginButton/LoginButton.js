function LoginButton({onClick}) {

    return (
        // <!--Login button-->
        <div className="d-grid gap-2">
            <button onClick={onClick} className=" btn background bolder" type="submit">Login</button>
        </div>
    );
}

export default LoginButton;