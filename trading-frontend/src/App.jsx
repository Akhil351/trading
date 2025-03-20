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

function App() {
  const isAuthenticated = false; 

  return (
    <>
      {!isAuthenticated ? (
        <Auth/>
      ) : (
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
      )}
    </>
  );
}

export default App;
