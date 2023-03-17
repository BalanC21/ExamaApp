import React, {useState} from 'react';
import ContactComponent from "./ContactComponent";

const ContactsPage = () => {
    const [numbers, setNumbers] = useState([1, 2])
    return (
        <div>
            <div className="search-box">
                <button className="btn-search"><i className="fas fa-search"></i></button>
                <input type="text" className="input-search" placeholder="Type to Search..."/>
            </div>
            <div style={{display: "flex"}}>
                {numbers.map((number, i) => <ContactComponent key={i} index={number}/>)}
            </div>
        </div>
    );
}

export default ContactsPage;
