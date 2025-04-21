import axios from "axios";
import {
  FETCH_COIN_LIST_FAILURE,
  FETCH_COIN_LIST_REQUEST,
  FETCH_COIN_LIST_SUCCESS,
  GET_USER_FAILURE,
  GET_USER_REQUEST,
  GET_USER_SUCCESS,
  LOGIN_FAILURE,
  LOGIN_REQUEST,
  LOGIN_SUCCESS,
  REGISTER_FAILURE,
  REGISTER_REQUEST,
  REGISTER_SUCCESS,
} from "./ActionTypes";

export const register = (userData) => async (dispatch) => {
  dispatch({ type: REGISTER_REQUEST });
  const baseUrl = "http://localhost:3510";

  try {
    const response = await axios.post(`${baseUrl}/auth/signup`, userData);
    const data = response.data;

    if (data.error != null) {
      throw new Error(data.error);
    }

    dispatch({ type: REGISTER_SUCCESS, payload: data.data.jwt });
    localStorage.setItem("jwt", data.data.jwt);
  } catch (error) {
    dispatch({ type: REGISTER_FAILURE, payload: error.message });
    console.error("Registration failed:", error.message);
  }
};

export const login = (userData) => async (dispatch) => {
  dispatch({ type: LOGIN_REQUEST });
  const baseUrl = "http://localhost:3510";

  try {
    const response = await axios.post(`${baseUrl}/auth/signin`, userData.data);
    const data = response.data;

    if (data.error != null) {
      throw new Error(data.error);
    }

    const user = data.data;
    dispatch({ type: LOGIN_SUCCESS, payload: user.jwt });
    localStorage.setItem("jwt", user.jwt);
    userData.navigate("/");
  } catch (error) {
    dispatch({ type: LOGIN_FAILURE, payload: error.message });
    console.error("Login failed:", error.message);
  }
};

export const getUser = (jwt) => async (dispatch) => {
  dispatch({ type: GET_USER_REQUEST });
  const baseUrl = "http://localhost:3510";

  try {
    const response = await axios.get(`${baseUrl}/api/users/profile`, {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    });

    const data = response.data;
    if (data.error != null) {
      throw new Error(data.error);
    }

    const user = data.data;
    dispatch({ type: GET_USER_SUCCESS, payload: user });
  } catch (error) {
    dispatch({ type: GET_USER_FAILURE, payload: error.message });
    console.error("Fetching user failed:", error.message);
  }
};

export const logout = () => (dispatch) => {
  // Clear the local storage
  localStorage.clear();

  // Dispatch the LOGOUT action
  dispatch({ type: "LOGOUT" });
};

export const getCoinList = (page) => async (dispatch) => {
  dispatch({ type: FETCH_COIN_LIST_REQUEST });
  const baseUrl = "http://localhost:3510";

  try {
    const response = await axios.get(`${baseUrl}/coins?page=${page}`);
    const data = response.data;
    console.log(data.data);
    if (data.error != null) {
      throw new Error(data.error);
    }

    dispatch({ type: FETCH_COIN_LIST_SUCCESS, payload: data.data });
  } catch (error) {
    dispatch({ type: FETCH_COIN_LIST_FAILURE, payload: error.message });
    console.error(error.message);
  }
};
