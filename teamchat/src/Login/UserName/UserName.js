function UserName({ value, onChange }){
    return(
        // <!--Username-->
        <input required value={value} onChange={onChange} className="form-control marginMedium " type="text" placeholder="Username"></input>
    );
}

export default UserName;