import LeftPanel from "../LeftPanel/LeftPanel";
import RightPanel from "../RightPanel/RightPanel";
import { useState, useEffect } from "react";
import socket from "../../socket";
//the chat block contains the chat and the contacts panel
function BigBlock({ me }) {

  const [selectedContact, setSelectedContact] = useState(null);
  const [sentList, setSentList] = useState([]);
  //lists of contats
  const [contactsList, setContactList] = useState([]);
  const [originalContactsList, setOriginalContactsList] = useState([]);

  //this works when a user recieves a message and he is connected.
  //he then will be updated automatically on the message
  useEffect(() => {
    socket.on('receive', function (messagePackage) {
      setSentList(prevSentList => [...prevSentList, messagePackage.message]);
      async function getContactsList() {
        const res = await fetch('http://localhost:5000/api/Chats', {
          headers: {
            'Content-Type': 'application/json',
            Authorization: 'bearer ' + me.token // attach the token
          },
        });
        const fetchedData = await res.json();
        setContactList(fetchedData);
        setOriginalContactsList(fetchedData);
      }

      getContactsList();
    });

    return () => {
      socket.off('receive'); // Cleanup the event listener on component unmount
    };
  }, [selectedContact]);

  async function getMessagesForContact(contact, me) {
    const res = await fetch(`http://localhost:5000/api/Chats/${contact.id}/Messages`, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'bearer ' + me.token // attach the token
      },
    });
    const fetchedData = await res.json();
    setSentList(fetchedData);
  };


  //delete contact user from the chat
  const deleteContact = async function () {
    try {
      //delete request
      const res = await fetch(`http://localhost:5000/api/Chats/${selectedContact.id}`, {
        method: 'delete',
        headers: {
          'Content-Type': 'application/json',
          Authorization: 'bearer ' + me.token // attach the token
        },
      });

      if (res.ok) {
        //set the contact chat- remove the contact from chat list
        setSelectedContact(null);

        async function getContactsList() {
          const res = await fetch('http://localhost:5000/api/Chats', {
            headers: {
              'Content-Type': 'application/json',
              Authorization: 'bearer ' + me.token // attach the token
            },
          });
          const fetchedData = await res.json();
          setContactList(fetchedData);
          setOriginalContactsList(fetchedData);
        }

        getContactsList();
      } else {
        console.log('Error:', res.status);
      }
    } catch (error) {
      console.log('Error:', error);
    }
  };





  //each time sent message we add it to the sentList
  const addMassage = async function (message) {
    const sendMessage = {
      "msg": message,
    };
    const res = await fetch(`http://localhost:5000/api/Chats/${selectedContact.id}/Messages`, {
      method: 'post',
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'bearer ' + me.token // attach the token
      },
      body: JSON.stringify(sendMessage),
    });
    if (res.ok) {
      const result = await res.text();
      const newMessage = JSON.parse(result);
      setSentList([...sentList, newMessage]);
      setSelectedContact(selectedContact);

      async function getContactsList() {
        const res = await fetch('http://localhost:5000/api/Chats', {
          headers: {
            'Content-Type': 'application/json',
            Authorization: 'bearer ' + me.token // attach the token
          },
        });
        const fetchedData = await res.json();
        setContactList(fetchedData);
        setOriginalContactsList(fetchedData);
      }

      getContactsList();
      console.log(selectedContact);
      const messagePackage = {
        message: newMessage,
        contact: selectedContact
      };
      socket.emit('message', messagePackage);

    } else {
      console.log('Error:', res.status);
    }
  }

  //setter for the selected contact
  const changeContact = function (contact) {
    setSelectedContact(contact);
    getMessagesForContact(contact, me);
  };

  return (
    <div id="frame">

      {/* contacts, profile and search for contacts */}
      <LeftPanel changeContact={changeContact} sentList={sentList} me={me}
        contactsList={contactsList} setContactList={setContactList}
        originalContactsList={originalContactsList}
        setOriginalContactsList={setOriginalContactsList} />
      {/* messages, contact profile and input messages */}
      <RightPanel selectedContact={selectedContact} me={me}
        sentList={sentList} addMassage={addMassage} deleteContact={deleteContact} />

    </div>
  );
}

export default BigBlock;