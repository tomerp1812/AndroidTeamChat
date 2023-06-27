import React, { useState } from 'react';
function UserName({ value, onChange }) {
    const [isFocused, setIsFocused] = useState(false);
//when the user is on the field, the conditions for the username name are displayed

    const handleFocus = () => {
      setIsFocused(true);
    };
  
    const handleBlur = () => {
      setIsFocused(false);
    };
    return (
       <div>
            <input value={value} onChange={onChange} className="form-control marginSmall" type="text" required placeholder="Username" onFocus={handleFocus} onBlur={handleBlur} />
            {isFocused  && <p className='font_error'>Username must be between 2-10 characters long</p>}
       </div>
    );
}

export default UserName;