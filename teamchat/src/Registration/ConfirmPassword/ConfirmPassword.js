import React from 'react';

function ConfirmPassword({value,onChange}) {
    return (
        <input required onChange={onChange}  value={value} className="form-control marginSmall" type="password" placeholder="Confirm Password"></input>

    );
}

export default ConfirmPassword;