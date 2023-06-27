import Add from "./Add";
import AddNewContactModal from "./AddNewContactModal";

// the modal that is beeing showed if a person pressed on AddFriend
function Modal({ newContact }) {
    return (
        <div className="modal fade" id="exampleModal" tabIndex="-1" aria-labelledby="exampleModalLabel"
            aria-hidden="true">
            <div className="modal-dialog">
                <div className="modal-content">
                    <AddNewContactModal />
                    <Add newContact={newContact} />
                </div>
            </div>
        </div>
    );
}

export default Modal;