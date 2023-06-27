import { useEffect, useState } from "react";
//the messages that i sent to my contact, and my image
function SentAndRecieve({ Massage, contact, me }) {
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
  if (Massage.sender.username === me.username) {
    return (
      <li className="sent">
        <img src={data.profilePic} alt="" />
        <p className="messageMargin">{Massage.content}</p>
      </li>
    );
  } else {
    return (
      <li className="replies">
        <img src={contact.user.profilePic} />
        <p className="messageMargin">{Massage.content}</p>
      </li>
    );
  }

}

export default SentAndRecieve;