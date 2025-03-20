import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { BookmarkFilledIcon } from "@radix-ui/react-icons";
import { BookMarkedIcon, DotIcon } from "lucide-react";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";

import React from "react";
import TradingForm from "./TradingForm";
import StockChart from "./StockChart";

export default function StockDetails() {
  return (
    <div className="p-5 mt-5">
      <div className="flex justify-between">
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
              <p className="text-xl font-bold">$6554</p>
              <p className="text-red-600">
                <span>-1319049822.578</span>
                <span>-0.29803%</span>
              </p>
            </div>
          </div>
        </div>
        <div className="flex items-center gap-5">
          <Button>
            {true ? (
              <BookmarkFilledIcon className="h-6 w-6" />
            ) : (
              <BookMarkedIcon className="h-6 w-6" />
            )}
          </Button>
          <Dialog>
            <DialogTrigger>
              <Button size="lg">Tread</Button>
            </DialogTrigger>
            <DialogContent>
              <DialogHeader>
                <DialogTitle>How Much Do you want to spend?</DialogTitle>
              </DialogHeader>
              <TradingForm />
            </DialogContent>
          </Dialog>
        </div>
      </div>
      <div className="mt-14">
        <StockChart />
      </div>
    </div>
  );
}
