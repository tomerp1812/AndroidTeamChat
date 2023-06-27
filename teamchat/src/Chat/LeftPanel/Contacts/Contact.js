// the contact that is being shown on the left
function Contact({ contact, isSelected, onSelectContact, sentList }) {
  // const variable to check if a contact is being selected
  const selectedClass = isSelected ? "selected" : "";
  // Extract the last message from the contact object
  const { lastMessage } = contact;

  // Extract the content and timestamp from the last message
  const { content, created } = lastMessage || {};

  // Check if the content exists and its length exceeds 20 characters
const trimmedContent = content && content.length > 20 ? `${content.slice(0, 20)}...` : content;

  // Convert the timestamp to a Date object
  const timestamp = new Date(created);

  // Convert the timestamp to a formatted date string
  const formattedDate = lastMessage ? timestamp.toLocaleString("en-GB", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "numeric",
    minute: "numeric",
    second: "numeric"
  }) : '';

  // Show the contact, their last message, its time, and their picture
  // Also, if we hover or select a contact, it shows a different color
  return (
    <li onClick={() => onSelectContact(contact)} className={`contact ${selectedClass}`}>
      <div className="wrap">
        <span className="contact-status"></span>
        <img src={contact.user.profilePic} alt="" />
        <div className="meta">
          <p className="name boldFont">{contact.user.displayName}</p>
          <p className="preview font_medium">{trimmedContent}</p>
          <span className="font_small">{formattedDate}</span>
        </div>
      </div>
    </li>
  );
}

export default Contact;
