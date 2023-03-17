import React from 'react';
import {MDBCardImage} from 'mdb-react-ui-kit';

const ContactComponent = ({index}) => {
    if (index == null)
        index = 1;
    return (
        <div style={{display: "flex", marginTop: 76, marginLeft: 10, marginRight: 10}}>
            <div style={{backgroundColor: "transparent"}}>
                <div style={{justifyContent: "center"}} className="justify-content-center">
                    <div style={{borderRadius: '15px'}}>
                        <div className="p-4">
                            <div style={{display: "flex", backgroundColor: "white", padding: 10, borderRadius: 20}}
                                 className="d-flex text-black">
                                <div style={{flexShrink: 0}} className="flex-shrink-0">
                                    <MDBCardImage
                                        style={{width: '180px', borderRadius: '10px'}}
                                        src={`https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-profiles/avatar-${index}.webp`}
                                        alt='Generic placeholder image'
                                        fluid/>
                                </div>
                                <div style={{flexGrow: 1, margin: 3}} className="flex-grow-1 ms-3">
                                    <div style={{color: "black"}}>Danny McLoan</div>
                                    <div>Senior Journalist</div>

                                    <div className="d-flex justify-content-start rounded-3 p-2 mb-2"
                                         style={{
                                             // display: "flex",
                                             justifyContent: "flex-start",
                                             backgroundColor: 'gray',
                                             padding: 10,
                                             borderRadius: 10,
                                             maxWidth: "fit-content",
                                             marginTop: 10
                                         }}>
                                        <div style={{display: "flex"}}>
                                            <p className="">PhoneNumber:</p>
                                            <p className="mb-0"> 0249941413</p>
                                        </div>
                                        <div style={{display: "flex"}}>
                                            <p>Money Owned:</p>
                                            <p className="mb-0">976</p>
                                        </div>
                                    </div>
                                    <div style={{
                                        display: "flex",
                                        marginTop: 35,
                                        backgroundColor: "white",
                                    }}>
                                        <button style={{
                                            backgroundColor: "#4681f4",
                                            borderRadius: 10,
                                            color: "black",
                                            border: "none",
                                            padding: 3,
                                            marginLeft: 4,
                                            marginTop: 15
                                        }}>Ask For
                                            Money
                                        </button>
                                        <button style={{
                                            backgroundColor: "#d42150",
                                            borderRadius: 10,
                                            color: "black",
                                            border: "none",
                                            padding: 5,
                                            marginLeft: 4,
                                            marginTop: 15
                                        }}>Send
                                            Money
                                        </button>
                                        <button
                                            style={{
                                                backgroundColor: "#33b249",
                                                borderRadius: 10,
                                                color: "black",
                                                border: "none",
                                                padding: 3,
                                                marginLeft: 4,
                                                marginTop: 15
                                            }}>Transactions
                                            History
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ContactComponent;
