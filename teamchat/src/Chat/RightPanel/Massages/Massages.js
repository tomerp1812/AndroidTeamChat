import SentAndRecieveListResults from "./SentAndRecieve/SentAndRecieveListResults";

function Massages({ sentList, contact, me }) {

    return (
        // <!--Massages-->
        <div className="messages">
            <ul className="listClass">
                <SentAndRecieveListResults Massages={sentList} contact={contact} me={me} />
                {/* <Replied /> */}
            </ul>
        </div>
    );
}

export default Massages;