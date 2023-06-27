import React, { useState } from 'react';

function AddPicture({onChange}) {
    const [imageSrc, setImageSrc] = useState('/profilePictures/pictureImage.png');

    const handleFileChange = (event) => {
        const file = event.target.files[0];
        const reader = new FileReader();

        reader.onload = () => {
            setImageSrc(reader.result);
            // Pass imageSrc as an argument to onChange function
            onChange(reader.result);
        };

        if (file) {
            reader.readAsDataURL(file);
        }

        onChange(reader.result);
    };

    return (
        <div>
            <input required accept="image/*" onChange={handleFileChange} className="form-control marginMedium" type="file" id="formFileDisabled"></input>
            <img src={imageSrc} className="marginMedium rounded mx-auto d-block loginImage"></img>
        </div>
    );
}

export default AddPicture;
