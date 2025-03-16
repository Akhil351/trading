import { Button } from "@/components/ui/button";
import React, { useState } from "react";
import AssetTable from "./AssetTable";
import StockChart from "./StockChart";
import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { Cross1Icon, DotIcon } from "@radix-ui/react-icons";
import { MessageCircle } from "lucide-react";
import { Input } from "@/components/ui/input";

export default function Home() {
  const [category, setCategory] = useState("all");
  const [inputValue, setInputValue] = useState("");
  const [isBotRelease, setIsBotRelease] = useState(false);
  const handleBotRelease = () => {
    setIsBotRelease(!isBotRelease);
  };
  const handleCategory = (value) => {
    setCategory(value);
  };

  const handleChange = (e) => {
    setInputValue(e.target.value);
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      console.log(inputValue);
      setInputValue("");
    }
  };

  return (
    <div className="relative">
      <div className="lg:flex">
        <div className="lg:w-[50%] border-r">
          <div className="p-3 flex items-center gap-4">
            <Button
              onClick={() => handleCategory("all")}
              variant={category === "all" ? "default" : "outline"}
              className="rounded-full"
            >
              All
            </Button>
            <Button
              onClick={() => handleCategory("top50")}
              variant={category === "top50" ? "default" : "outline"}
              className="rounded-full"
            >
              Top 50
            </Button>

            <Button
              onClick={() => handleCategory("topGainer")}
              variant={category === "topGainer" ? "default" : "outline"}
              className="rounded-full"
            >
              Top Gainers
            </Button>

            <Button
              onClick={() => handleCategory("topLoser")}
              variant={category === "topLoser" ? "default" : "outline"}
              className="rounded-full"
            >
              Top Losers
            </Button>
          </div>
          <AssetTable />
        </div>
        <div className="hidden lg:block lg:w-[50%] p-5">
          <StockChart />
          <div className="flex gap-5 items-center">
            <div>
              <Avatar>
                <AvatarImage src="https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400" />
              </Avatar>
            </div>
            <div>
              <div className="flex items-center gap-2">
                <p>BTC</p>
                <DotIcon className="text-gray-400" />
                <p className="text-gray-400">BitCoin</p>
              </div>
              <div className="flex items-end gap-2">
                <p className="text-xl font-bold">5464</p>
                <p className="text-red-600">
                  <span>-1319049822.578</span>
                  <span>(-0.29803%)</span>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <section className="absolute bottom-5 right-5 z-40 flex flex-col justify-end items-end gap-2">
        {isBotRelease && (
          <div className="rounded-md w-[20rem] md:w-[25rem] lg:w-[25rem] h-[70vh] bg-slate-900">
            <div className="flex justify-between items-center border-b px-6 h-[12%]">
              <p>Chat bot</p>
              <Button onClick={handleBotRelease} variant="ghost" size="icon">
                <Cross1Icon />
              </Button>
            </div>

            <div className="h-[76%] flex flex-col overflow-y-auto gap-5 px-5 py-2 scroll-container">
              {/* Bot Message */}
              <div className="self-start pb-5 w-auto">
                <div className="px-5 py-3 rounded-md bg-slate-800 w-auto">
                  <p>Hi, Akhileswar</p>
                  <p>You can ask any crypto-related question</p>
                  <p>Like price, market cap, etc.</p>
                </div>
              </div>

              {/* User Message */}
              {[1, 1].map((item, index) => (
                <div
                  key={index}
                  className={
                    index % 2 == 0
                      ? "self-start pb-5 w-auto"
                      : "self-end pb-5 w-auto"
                  }
                >
                  {index % 2 == 0 ? (
                    <div className="px-5 py-3 rounded-md bg-slate-800 w-auto">
                      <p>What is the value of Bitcoin?</p>
                    </div>
                  ) : (
                    <div className="px-5 py-3 rounded-md bg-slate-800 w-auto">
                      <p>The price of Bitcoin is 69,268</p>
                    </div>
                  )}
                </div>
              ))}
            </div>
            <div className="h-[12%] border-t">
              <Input
                className="w-full h-full border-none outline-none"
                placeholder="Write prompt"
                onChange={handleChange}
                value={inputValue}
                onKeyDown={handleKeyDown}
              />
            </div>
          </div>
        )}

        <div className="relative w-[10rem] cursor-pointer">
          <Button
            onClick={handleBotRelease}
            className="w-full h-[3rem] gap-2 items-center group"
          >
            <MessageCircle
              size={30}
              className="fill-[#1e293b] -rotate-90 stroke-none group-hover:fill-[#1a1a1a]"
            />
            <span className="text-2xl">Chat Bot</span>
          </Button>
        </div>
      </section>
    </div>
  );
}
