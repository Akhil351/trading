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

function App() {
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />}></Route>
        <Route path="/portfolio" element={<Portfolio />}></Route>
        <Route path="/activity" element={<Activity />}></Route>
        <Route path="/wallet" element={<Wallet />}></Route>
        <Route path="/withdrawal" element={<Withdrawal />}></Route>
        <Route path="/payment-details" element={<PaymentDetails />}></Route>
        <Route path="/market/:id" element={<StockDetails />}></Route>
        <Route path="/watchlist" element={<WatchList />}></Route>
        <Route path="/profile" element={<Profile />}></Route>
        <Route path="/search" element={<SearchCoin />}></Route>
        <Route path="*" element={<NotFound />}></Route>
      </Routes>
    </>
  );
}

export default App;
