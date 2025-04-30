import {
  REGISTER_REQUEST,
  REGISTER_SUCCESS,
  REGISTER_FAILURE,
  LOGIN_REQUEST,
  GET_USER_REQUEST,
  LOGIN_FAILURE,
  GET_USER_FAILURE,
  LOGIN_SUCCESS,
  GET_USER_SUCCESS,
  LOGOUT,
  FETCH_COIN_BY_ID_REQUEST,
  FETCH_COIN_DETAILS_REQUEST,
  FETCH_COIN_LIST_REQUEST,
  SEARCH_COIN_REQUEST,
  FETCH_TOP_50_COIN_REQUEST,
  FETCH_MARKET_CHART_REQUEST,
  FETCH_COIN_LIST_SUCCESS,
  FETCH_TOP_50_COIN_SUCCESS,
  FETCH_MARKET_CHART_SUCCESS,
  FETCH_COIN_BY_ID_SUCCESS,
  SEARCH_COIN_SUCCESS,
  FETCH_COIN_DETAILS_SUCCESS,
  FETCH_MARKET_CHART_FAILURE,
  FETCH_COIN_LIST_FAILURE,
  SEARCH_COIN_FAILURE,
  FETCH_COIN_BY_ID_FAILURE,
  FETCH_COIN_DETAILS_FAILURE,
  FETCH_TOP_50_COIN_FAILURE,
} from "./ActionTypes";
import { data } from "react-router-dom";

const userInitialState = {
  user: null,
  loading: false,
  error: null,
  jwt: null,
};
const coinInitialState = {
  coinList: [],
  top50: [],
  searchCoinList: [],
  marketChart: { data: [], loading: false },
  coinById: null,
  coinDetails: null,
  loading: false,
  error: null,
};

const authReducer = (state = userInitialState, action) => {
  switch (action.type) {
    case REGISTER_REQUEST:
    case LOGIN_REQUEST:
    case GET_USER_REQUEST:
      return { ...state, loading: true, error: null };
    case REGISTER_SUCCESS:
    case LOGIN_SUCCESS:
      return { ...state, loading: false, error: null, jwt: action.payload };
    case GET_USER_SUCCESS:
      return { ...state, loading: false, error: null, user: action.payload };
    case REGISTER_FAILURE:
    case LOGIN_FAILURE:
    case GET_USER_FAILURE:
      return { ...state, loading: false, error: action.payload };

    case LOGOUT:
      return userInitialState;
    default:
      return state;
  }
};

const coinReducer = (state = coinInitialState, action) => {
  switch (action.type) {
    case FETCH_COIN_LIST_REQUEST:
    case FETCH_COIN_BY_ID_REQUEST:
    case FETCH_COIN_DETAILS_REQUEST:
    case SEARCH_COIN_REQUEST:
    case FETCH_TOP_50_COIN_REQUEST:
      return { ...state, loading: true, error: null };
    case FETCH_MARKET_CHART_REQUEST:
      return {
        ...state,
        marketChart: { data: [], loading: true },
        error: null,
      };
    case FETCH_COIN_LIST_SUCCESS:
      return {
        ...state,
        coinList: action.payload,
        loading: false,
        error: null,
      };
    case FETCH_TOP_50_COIN_SUCCESS:
      return {
        ...state,
        top50: action.payload,
        loading: false,
        error: null,
      };
    case FETCH_MARKET_CHART_SUCCESS:
      return {
        ...state,
        marketChart: { data: action.payload.price, loading: false },
        error: null,
      };
    case FETCH_COIN_BY_ID_SUCCESS:
      return {
        ...state,
        coinDetails: action.payload,
        loading: false,
        error: null,
      };
    case SEARCH_COIN_SUCCESS:
      return {
        ...state,
        searchCoinList: action.payload.coins,
        loading: false,
        error: null,
      };
    case FETCH_COIN_DETAILS_SUCCESS:
      return {
        ...state,
        coinDetails: action.payload,
        loading: false,
        error: null,
      };
    case FETCH_MARKET_CHART_FAILURE:
      return {
        ...state,
        marketChart: { loading: false, data: [] },
        error: null,
      };
    case FETCH_COIN_LIST_FAILURE:
    case SEARCH_COIN_FAILURE:
    case FETCH_COIN_BY_ID_FAILURE:
    case FETCH_COIN_DETAILS_FAILURE:
    case FETCH_TOP_50_COIN_FAILURE:
      return { ...state, loading: false, error: action.payload };
    default:
      return state;
  }
};

export { authReducer, coinReducer };
