import RegistrationTitle from './RegistrationTitle/RegistrationTitle';
import UserName from './UserName/UserName';
import Password from './Password/Password';
import DisplayName from './DisplayName/DisplayName';
import AddPicture from './AddPicture/AddPicture';
import RegisterButton from './RegisterButton/RegisterButton';
import LoginPageLink from './LoginPageLink/LoginPageLink';
import ConfirmPassword from './ConfirmPassword/ConfirmPassword';
import WebTitle from './WebTitle/WebTitle';
import { useState } from 'react';
import { useNavigate } from "react-router-dom";

function Registration({ list, setList }) {
  const SIZEOFPIC = 1024*350;
  const navigate = useNavigate();
  const [errorMessage, setErrorMessage] = useState('');

  // Username
  const [name, setName] = useState('');
  const handleNameChange = (event) => {
    setName(event.target.value);
    setErrorMessage('');
  };
  // Password
  const [password, setPassword] = useState('');
  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
    setErrorMessage('');

  };
  //Confirm Password
  const [confirmPassword, setConfirmPassword] = useState('');
  const handleConfirmPasswordChange = (event) => {
    setConfirmPassword(event.target.value);
    setErrorMessage('');

  };
  //Display name
  const [displayName, setDisplayName] = useState('');
  const handleDisplayName = (event) => {
    setDisplayName(event.target.value);
    setErrorMessage('');

  }
  //Add picture
  const [addPicture, setAddPicture] = useState('');
  const handleAddPicture = (imageSrc) => {
    const encoder = new TextEncoder();
    const byteLength = encoder.encode(imageSrc).length;
  
    //check size of Picture depends on the fact that it works with UTF-8
    //and therefor checking the size of string gives a good estimation
    //of size of picture it self
    if (byteLength <= SIZEOFPIC) {
      setAddPicture(imageSrc);
      setErrorMessage('');
    } else {
      setAddPicture('');
      setErrorMessage('Picture size too large, please select a different picture');
    }
  };


  const handleSubmit = async (event) => {
    event.preventDefault();
    //handle username
    if (name.trim().length < 2 || name.trimEnd().length > 10) {
      setErrorMessage('Username must be between 2 and 10 characters.');
      return;
    }

    //handle password
    const regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,16}$/;
    if (!regex.test(password)) {
      setErrorMessage('Password must be 8-16 characters long and contain English letters and numbers.');
      return
    }
    //handle confirm password
    if (password !== confirmPassword) {
      setErrorMessage('Passwords do not match.');
      return;
    }
    //handle display name
    if (displayName.trim().length < 2 || displayName.trimEnd().length > 10) {
      setErrorMessage('Display name must be between 2 and 10 characters.');
      return;
    }
    //handle picture
    if (addPicture.trim().length == 0) {
      setErrorMessage('Please select a picture.');
      return;
    }
    //registration successful

    const item = {
      "username": name,
      "password": password,
      "displayName": displayName,
      "profilePic": addPicture
    };

    const res = await fetch('http://localhost:5000/api/Users', {
      'method': 'post',
      'headers': {
        'Content-Type': 'application/json',
      },
      'body': JSON.stringify(item),
    });

    if(res.ok){
      // setList([...list, item]);
      navigate("/");
    }else{
      setErrorMessage('Username already exists.');
      return;
    }
   
  };
  return (
    <div className="background">
      <div className="login-box">
        <form>
          <WebTitle />
          <RegistrationTitle />
          <UserName value={name} onChange={handleNameChange} />
          <Password value={password} onChange={handlePasswordChange} />
          <ConfirmPassword value={confirmPassword} onChange={handleConfirmPasswordChange} />
          <DisplayName value={displayName} onChange={handleDisplayName} />
          <AddPicture value={addPicture} onChange={handleAddPicture} />
          {errorMessage && <h6 className='font_error'>{errorMessage}</h6>}
          <RegisterButton onClick={handleSubmit} />
          <LoginPageLink />
        </form>
      </div>
    </div>
  );
}

export default Registration;
