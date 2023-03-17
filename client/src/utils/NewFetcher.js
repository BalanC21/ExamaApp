import axios from "axios";

const BASE_URL = process.env.REACT_APP_BASE_URL;

//todo Vezi axiosFetcher-ul de ce nu merge?!!

// let token = localStorage.getItem("token");
// const axiosFetcher = axios.create({
//     baseURL: BASE_URL,
//     headers: {
//         Authorization: "Bearer " + localStorage.getItem("token")
//     }
// })

//todo Poate faci una globala!! Si adaugi mutate

// export const useRequest = (path) => {
//     if (!path) {
//         throw new Error('UseRequest Path is required')
//     }
//     const {data, error, isLoading} = useSWR(path, newGetSWR)
//
//     return {data, error, isLoading}
// }


const newGetSWR = async (url) => await axios.get(BASE_URL + url, {
    headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
    }
})
    .then((res) => res.data);

let logInPost = async (url, payload) => await axios.post(BASE_URL + url, payload).then(r => r.data);

let newPostSWR = async (url, payload) => await axios.post(BASE_URL + url, payload, {
    headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
    }
})
    .then(r => r.data);

let newPatchSWR = async (url, payload) => await axios.patch(BASE_URL + url, payload, {
    headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
    }
})
    .then((res) => res.data);

let newDeleteSWR = async (url) => await axios.delete(BASE_URL + url, {
    headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
    }
})
    .then((res) => res.data);


export {newGetSWR, newPatchSWR, newDeleteSWR, newPostSWR, logInPost};
