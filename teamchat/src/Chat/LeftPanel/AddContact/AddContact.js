import Modal from "./Modal";

function AddContact({ newContact }) {

  return (
    //  <!--Add contacts-->
    <div id="bottom-bar">
      <button id="addcontact" data-bs-toggle="modal" data-bs-target="#exampleModal"><span>Add contact</span>
        <i className="bi bi-person-plus"></i>
      </button>
      {/* the modal that is beeing displayed */}
      <Modal newContact={newContact} />
    </div>
  );
}

export default AddContact;