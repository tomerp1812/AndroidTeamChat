import Contact from "./Contact";
import { useState } from "react";
//creating for all the contacts a ul
function ContactListResults({ contacts, changeContact, me, sentList }) {

  //selected contact setter and getter
  const [selectedContact, setSelectedContact] = useState(null);

  //if a contacts selected we want to set him as selected, and
  //send it to the right side with changeContact to see his messages
  //and see his profile
  const onSelectContact = (contact) => {
    setSelectedContact(contact);
    changeContact(contact);
  };

// Make a list of all the contacts
const contactsList = contacts
  .slice() // Create a copy of the contacts array
  .sort((a, b) => {
    // Check if there are any messages for the contacts
    const aLastMessageTime = a.lastMessage ? new Date(a.lastMessage.created).getTime() : 0;
    const bLastMessageTime = b.lastMessage ? new Date(b.lastMessage.created).getTime() : 0;
    return bLastMessageTime - aLastMessageTime;
  })
  .map((contact, key) => {
    const isSelected = contact === selectedContact;
    return (
      <Contact
        key={key}
        isSelected={isSelected}
        contact={contact}
        onSelectContact={onSelectContact}
        me={me}
        sentList={sentList}
      />
    );
  });



  return (
    //<!--contacts-->
    <div id="contacts">
      <ul className="listClass">
        {contactsList}
      </ul>
    </div>
  );
}

export default ContactListResults