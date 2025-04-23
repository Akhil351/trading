import axios from "axios";
import {
  FETCH_COIN_BY_ID_FAILURE,
  FETCH_COIN_BY_ID_REQUEST,
  FETCH_COIN_BY_ID_SUCCESS,
  FETCH_COIN_DETAILS_FAILURE,
  FETCH_COIN_DETAILS_REQUEST,
  FETCH_COIN_DETAILS_SUCCESS,
  FETCH_COIN_LIST_FAILURE,
  FETCH_COIN_LIST_REQUEST,
  FETCH_COIN_LIST_SUCCESS,
  FETCH_MARKET_CHART_FAILURE,
  FETCH_MARKET_CHART_REQUEST,
  FETCH_MARKET_CHART_SUCCESS,
  FETCH_TOP_50_COIN_FAILURE,
  FETCH_TOP_50_COIN_REQUEST,
  FETCH_TOP_50_COIN_SUCCESS,
  GET_USER_FAILURE,
  GET_USER_REQUEST,
  GET_USER_SUCCESS,
  LOGIN_FAILURE,
  LOGIN_REQUEST,
  LOGIN_SUCCESS,
  REGISTER_FAILURE,
  REGISTER_REQUEST,
  REGISTER_SUCCESS,
  SEARCH_COIN_FAILURE,
  SEARCH_COIN_REQUEST,
  SEARCH_COIN_SUCCESS,
} from "./ActionTypes";
import api, { API_BASE_URL } from "@/config/api";

export const register = (userData) => async (dispatch) => {
  dispatch({ type: REGISTER_REQUEST });

  try {
    const response = await axios.post(`${API_BASE_URL}/auth/signup`, userData);
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

  try {
    const response = await axios.post(
      `${API_BASE_URL}/auth/signin`,
      userData.data
    );
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

  try {
    const response = await axios.get(`${API_BASE_URL}/api/users/profile`, {
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

  try {
    const response = await axios.get(`${API_BASE_URL}/coins?page=${page}`);
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

export const getTop50CoinList = () => async (dispatch) => {
  dispatch({ type: FETCH_TOP_50_COIN_REQUEST });

  try {
    const response = await axios.get(`${API_BASE_URL}/coins/top50 `);
    const data = response.data;
    console.log(data.data);
    if (data.error != null) {
      throw new Error(data.error);
    }

    dispatch({ type: FETCH_TOP_50_COIN_SUCCESS, payload: data.data });
  } catch (error) {
    dispatch({ type: FETCH_TOP_50_COIN_FAILURE, payload: error.message });
    console.error(error.message);
  }
};

export const fetchMarketChart =
  ({ coinId, days, jwt }) =>
  async (dispatch) => {
    dispatch({ type: FETCH_MARKET_CHART_REQUEST });

    try {
      const response = await api.get(`/coins/${coinId}/chart?days=${days} `, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });
      const data = response.data;
      console.log(data.data);
      if (data.error != null) {
        throw new Error(data.error);
      }

      dispatch({ type: FETCH_MARKET_CHART_SUCCESS, payload: data.data });
    } catch (error) {
      dispatch({ type: FETCH_MARKET_CHART_FAILURE, payload: error.message });
      console.error(error.message);
    }
  };

export const fetchCoinById = (coinId) => async (dispatch) => {
  dispatch({ type: FETCH_COIN_BY_ID_REQUEST });

  try {
    const response = await api.get(`/coins/${coinId} `);
    const data = response.data;
    console.log(data.data);
    if (data.error != null) {
      throw new Error(data.error);
    }

    dispatch({ type: FETCH_COIN_BY_ID_SUCCESS, payload: data.data });
  } catch (error) {
    dispatch({ type: FETCH_COIN_BY_ID_FAILURE, payload: error.message });
    console.error(error.message);
  }
};

export const fetchCoinDetails =
  ({ coinId, jwt }) =>
  async (dispatch) => {
    dispatch({ type: FETCH_COIN_DETAILS_REQUEST });

    try {
      const response = await api.get(`/coins/details/${coinId} `, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });
      const data = response.data;
      console.log(data.data);
      if (data.error != null) {
        throw new Error(data.error);
      }

      dispatch({ type: FETCH_COIN_DETAILS_SUCCESS, payload: data.data });
    } catch (error) {
      dispatch({ type: FETCH_COIN_DETAILS_FAILURE, payload: error.message });
      console.error(error.message);
    }
  };

export const searchCoin = (keyword) => async (dispatch) => {
  dispatch({ type: SEARCH_COIN_REQUEST });

  try {
    const response = await api.get(`/coins/search?q=${keyword}`);
    const data = response.data;
    console.log(data.data);
    if (data.error != null) {
      throw new Error(data.error);
    }

    dispatch({ type: SEARCH_COIN_SUCCESS, payload: data.data });
  } catch (error) {
    dispatch({ type: SEARCH_COIN_FAILURE, payload: error.message });
    console.error(error.message);
  }
};
