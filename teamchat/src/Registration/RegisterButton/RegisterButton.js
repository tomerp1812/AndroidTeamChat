import React from 'react';

function RegisterButton({ onClick }) {
   
    return (
        <div className="d-grid gap-2 ">
            <button onClick={onClick} className="btn background bolder" type="submit">
                Register
            </button>
        </div>
    );
}

export default RegisterButton;
