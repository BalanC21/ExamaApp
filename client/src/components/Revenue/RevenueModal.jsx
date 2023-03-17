import React, {useState} from 'react';
import Modal from "react-bootstrap/Modal";
import {newPostSWR} from "../../utils/NewFetcher";
import {mutate} from "swr";
import {notifyAddRevenueError, notifyAddRevenueSuccess} from "../shared/Toasts";
import {ToastContainer} from "react-toastify";

const RevenueModal = () => {
    const [show, setShow] = useState(false);
    const REACT_APP_CRUD_REVENUE = process.env.REACT_APP_CRUD_REVENUE

    const [revenue, setRevenue] = useState({
        amount: 0,
        isRecursive: false,
        renewalDayOfMonth: 1
    })
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    function handle(e) {
        const newRevenue = {...revenue};
        newRevenue[e.target.id] = e.target.value;
        setRevenue(newRevenue)
    }

    function handleCheckBox(e) {
        const {checked} = e.target
        const newRevenue = {...revenue};
        newRevenue[e.target.id] = checked;
        setRevenue(newRevenue)
    }

    async function submit(e) {
        e.preventDefault();
        newPostSWR(REACT_APP_CRUD_REVENUE, revenue).then(handleClose).then(async () => {
            await mutate(REACT_APP_CRUD_REVENUE, revenue, false)
            notifyAddRevenueSuccess()
        }).catch(() => notifyAddRevenueError())
    }

    return (
        <div className={"modal-show"}>
            <ToastContainer/>
            <button className={"switch-modal"} onClick={handleShow} style={{marginRight: "2em"}}>
                Set Revenue &#x2b;
            </button>
            <Modal
                show={show}
                onHide={handleClose}
                backdrop="static"
                keyboard={true}
                className={"add-expense-modal-container"}>
                <Modal.Header closeButton>
                    <div className={"popup-container"}>
                        <button className={"close"} onClick={handleClose}>
                            &times;
                        </button>
                    </div>
                    <Modal.Title>Add Revenue</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form onSubmit={(e) => submit(e)}>
                        <label htmlFor={"amount"}>Amount</label>
                        <input type="number" id={"amount"} className="input input-amount"
                               onChange={(e) => handle(e)}/>
                        <label htmlFor={"renewalDayOfMonth"}>Set Renewal Day Of Month</label>
                        <input type="number" id={"renewalDayOfMonth"} className="input input-amount"
                               onChange={(e) => handle(e)}/>
                        <div style={{display: "flex"}}>
                            <label htmlFor={"isRecursive"}>Wanna Renew Each Month?</label>
                            <input style={{alignSelf: "center"}} type="checkbox" id={"isRecursive"}
                                   className="input input-amount"
                                   onChange={(e) => handleCheckBox(e)}/>
                        </div>
                        <button className={"submit-btn"}>Submit</button>
                    </form>
                </Modal.Body>
                <Modal.Footer>
                </Modal.Footer>
            </Modal>
        </div>
    )
};

export default RevenueModal;
