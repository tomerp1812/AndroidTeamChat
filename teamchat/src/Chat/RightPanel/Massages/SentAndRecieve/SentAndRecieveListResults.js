import SentAndRecieve from "./SentAndRecieve"
//a function that takes the list of all the messages,
//and filters only those that the id of the contact
//that im messaging to will be shown
function SentAndRecieveListResults({ Massages, contact, me }) {
    const sentList = Massages.map((Massage, key) => {
        return <SentAndRecieve key={key} Massage={Massage} me={me} contact={contact} />;
    });
    return (
        <>{sentList}</>
    );
}

export default SentAndRecieveListResults;