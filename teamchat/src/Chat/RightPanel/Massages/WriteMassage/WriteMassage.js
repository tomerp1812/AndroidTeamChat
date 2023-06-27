import { useRef } from "react";
function WriteMassage({ addMassage }) {

    //save a state of the letters that im writing
    const massageBox = useRef(null);
    //if enter or click on send this function will be called
    const sendMassage = function () {
        //if the message is not empty we want to send the message
        if (massageBox.current.value != "") {
            addMassage(massageBox.current.value);
            //empty the box after the message was sent
            massageBox.current.value = "";
        }
    }

    //if enter was pressed we also want to send a message
    const handleKeyDown = function (event) {
        if (event.key === "Enter") {
            sendMassage();
        }
    }

    return (
        //<!--message-input-->
        <div className="message-input">
            <div className="wrap">
                <input onKeyDown={handleKeyDown}
                    ref={massageBox} className="form-control"
                    type="text" placeholder="Write your message..."></input>
                <button onClick={sendMassage}
                    className="submit">Send  <i className="bi bi-envelope"></i></button>
            </div>
        </div>
    );
}

export default WriteMassage;