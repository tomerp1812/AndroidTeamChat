//when a person is searching for a contact we filter it to show only the contacts 
//that included those letters that he wrote in
export const doSearch = function (originalContactsList, q) {
    if (q === "") {
        return originalContactsList;
    } else {
        return originalContactsList.filter((contact) => contact.user.displayName.includes(q));
    }
}
