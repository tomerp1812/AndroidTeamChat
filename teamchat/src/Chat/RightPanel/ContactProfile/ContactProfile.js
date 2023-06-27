//a function that shows the image and the name of the contact we messaging with
function ContactProfile({ contact, deleteContact }) {

    if (!contact) {
        return (
            <>
                <div className="contact-profile"></div>
                <p className="boldFont contactBackgroundColor"></p>
            </>
        );
    }

    return (
        <div>
            <div>
                <button className=" bi bi-person-x-fill btn  deleteButton bi-size-xl text-danger" onClick={deleteContact}>
                </button>
            </div>
            <div className="contact-profile">
                <img src={contact.user.profilePic} alt="" />
                <p className="boldFont contactBackgroundColor">{contact.user.displayName}</p>
            </div>
        </div>
    );
}

export default ContactProfile;