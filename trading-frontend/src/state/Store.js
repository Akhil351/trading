import { applyMiddleware, combineReducers, legacy_createStore } from "redux";
import { thunk } from "redux-thunk";
import { authReducer, coinReducer } from "./Reducer";

const rootReducer = combineReducers({
  auth:authReducer,
  coin: coinReducer,
});

export const store = legacy_createStore(rootReducer, applyMiddleware(thunk));
