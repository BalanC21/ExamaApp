import axios from "axios";
import {useNavigate} from "react-router-dom";

export default function Logout() {
    const url = "http://localhost:8888/api/auth/logout";
    const navigate = useNavigate();
    axios.get(url).then(r => console.log(r));
    localStorage.clear()
    return navigate("/login");
}
