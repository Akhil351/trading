import { Route, Routes } from "react-router-dom";
import Home from "./page/Home";
import Navbar from "./page/Navbar";
import Portfolio from "./page/Portfolio";
import Activity from "./page/Activity";
import Wallet from "./page/Wallet";
import Withdrawal from "./page/Withdrawal";
import PaymentDetails from "./page/PaymentDetails";
import StockDetails from "./page/StockDetails";
import WatchList from "./page/WatchList";
import Profile from "./page/Profile";
import SearchCoin from "./page/SearchCoin";
import NotFound from "./page/NotFound";
import Auth from "./page/Auth";
import { useDispatch, useSelector } from "react-redux";
import { useEffect, useState } from "react";
import { getUser } from "./state/Action";

function App() {
  const dispatch = useDispatch();
  const auth = useSelector((store) => store.auth);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = auth.jwt || localStorage.getItem("jwt");

    if (token) {
      dispatch(getUser(token)).finally(() => setLoading(false));
    } else {
      setLoading(false);
    }
  }, [dispatch, auth.jwt]);

  if (loading)
    return (
      <div className="h-screen flex justify-center items-center">
        <div className="w-12 h-12 border-4 border-orange-500 border-dashed rounded-full animate-spin" />
      </div>
    ); // You can customize this

  return auth.user ? (
    <div>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/portfolio" element={<Portfolio />} />
        <Route path="/activity" element={<Activity />} />
        <Route path="/wallet" element={<Wallet />} />
        <Route path="/withdrawal" element={<Withdrawal />} />
        <Route path="/payment-details" element={<PaymentDetails />} />
        <Route path="/market/:id" element={<StockDetails />} />
        <Route path="/watchlist" element={<WatchList />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/search" element={<SearchCoin />} />
        <Route path="/login" element={<Auth />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </div>
  ) : (
    <Auth />
  );
}

export default App;
