import AddContact from "./AddContact/AddContact";
import Search from "./Search/Search";
import { useEffect } from "react";
import ContactListResults from "./Contacts/ContactListResults";
import Profile from "./Profile/Profile";
import { doSearch } from "./Search/doSearch";

//a side where the profile, search for contacts,
// the list of contacts and add contacts button
// is beeing showed
function LeftPanel({ changeContact, me, sentList, contactsList,
    setContactList, originalContactsList, setOriginalContactsList }) {

    useEffect(() => {
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
    }, []);


    //setter of the search for contacts
    const handleSearch = async function (q) {
        const searchedContactsList = doSearch(originalContactsList, q);
        setContactList(searchedContactsList);
    }

    //setter of the list of conntacts when a new contacts is beeing created
    //setter of the list of contacts when a new contact is being created
    const handleNewContact = async function (q) {
        const newContact = { "username": q };
        // Update the list on the server
        const res = await fetch('http://localhost:5000/api/Chats', {
            method: 'post',
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'bearer ' + me.token // attach the token
            },
            body: JSON.stringify(newContact),
        });
        if (res.ok) {
            const result = await res.text();
            // Update the two lists locally
            const newContact = JSON.parse(result);
            setOriginalContactsList([...originalContactsList, newContact]);
            setContactList([...contactsList, newContact]);
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
    };


    return (
        <div id="sidepanel">
            <Profile me={me} />
            <Search doSearch={handleSearch} />
            <ContactListResults sentList={sentList} contacts={contactsList} changeContact={changeContact} me={me} />
            <AddContact newContact={handleNewContact} />
        </div>
    );
}

export default LeftPanel;
