import React, { useState, useEffect } from 'react';

function Profile({ me }) {
    const [data, setData] = useState(null);

  useEffect(() => {
    const fetchProfilePic = async () => {
        const res = await fetch(`http://localhost:5000/api/Users/${me.username}`, {
            'headers': {
                'Content-Type': 'application/json',
                'Authorization': 'bearer ' + me.token // attach the token
            }
        });
        const fetchedData = await res.json();
        setData(fetchedData);
    };

    fetchProfilePic();
  }, [me.username, me.token]);


  if (!data) {
    return <p>Loading profile data...</p>;
  }
  
  return (
    <div id="profile">
      <div className="wrap">
        <img id="" src={data.profilePic} alt="" />
        <p className="boldFont">{data.displayName}</p>
      </div>
    </div>
  );
}

export default Profile;
