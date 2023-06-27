import { useRef } from "react";

//adding a contacts with Enter key and click
function Add({ newContact }) {
  const friendBox = useRef(null);
  // add friend if the add input is not empty
  const addFriend = function () {
    if (friendBox.current.value != "") {
      newContact(friendBox.current.value);
      friendBox.current.value = "";
    }
    // set focus to the button
    document.getElementById("add-contact-btn").focus();
  };

  // if enter was pressed go to addFriend
  const handleKeyDown = function (event) {
    if (event.key === "Enter") {
      addFriend();
    }
  };

  return (
    <>
      {/* the input */}
      <div className="modal-body">
        <input
          onKeyDown={handleKeyDown}
          ref={friendBox}
          className="form-control"
          type="text"
          placeholder="Contact's identifier"
        ></input>
      </div>
      {/* the button where if a person clicks it will add a new friend */}
      <div className="modal-footer">
        <button
          id="add-contact-btn" // add an id to the button
          onClick={addFriend}
          type="button"
          className="btn btn-secondary"
          data-bs-dismiss="modal"
        >
          Add
        </button>
      </div>
    </>
  );
}

export default Add;
