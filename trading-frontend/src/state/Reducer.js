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
} from "./ActionTypes";

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
  }
};

export { authReducer, coinReducer };
