import React, { useState } from 'react';
//when the user is on the field, the conditions for the password are displayed
function Password({ value, onChange }) {
    const [isFocused, setIsFocused] = useState(false);

    const handleFocus = () => {
        setIsFocused(true);
    };

    const handleBlur = () => {
        setIsFocused(false);
    };
    return (
        <div>
            <input required value={value} onChange={onChange} className="form-control marginSmall" type="password" placeholder="Password" onFocus={handleFocus} onBlur={handleBlur}></input>
            {isFocused && <p className='font_error'>Password must be 8-16 characters long - English letters and numbers</p>}
        </div>
    );
}

export default Password;