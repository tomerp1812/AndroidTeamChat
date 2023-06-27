import { useRef } from "react";

//search for fonctacts function
function Search({ doSearch }) {

    //in searchBox we store the current value of the input
    const searchBox = useRef(null);
    //we call doSearch each time a key was up
    const search = function () {
        doSearch(searchBox.current.value);
    }

    return (
        //<!--search text-->
        <div id="search">
            <label htmlFor=""><i className="fa fa-search" aria-hidden="true"></i></label>
            <input ref={searchBox} onKeyUp={search} type="text" placeholder="Search contacts..." />
        </div>
    );
}

export default Search;