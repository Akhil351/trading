import React from "react";
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "@/components/ui/sheet";
import { Button } from "@/components/ui/button";
import {
  DragHandleHorizontalIcon,
  MagnifyingGlassIcon,
} from "@radix-ui/react-icons";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import SideBar from "./SideBar";
import { useSelector } from "react-redux";

export default function Navbar() {
  const { user } = useSelector((store) => store.authReducer);

  return (
    <div
      className="px-2 py-3 border-b z-50 bg-background bg-opacity-0 sticky top-0 left-0 right-0
      flex justify-between items-center"
    >
      <div className="flex items-center gap-3">
        <Sheet>
          <SheetTrigger asChild>
            <Button variant="ghost" size="icon" className="rounded-full h-11 w-11">
              <DragHandleHorizontalIcon className="h-7 w-7" />
            </Button>
          </SheetTrigger>
          <SheetContent
            className="w-72 border-r-0 flex flex-col justify-center"
            side="left"
          >
            <SheetHeader>
              <SheetTitle>
                <div className="text-3xl flex justify-center items-center gap-1">
                  <Avatar className="h-10 w-10">
                    <AvatarImage src="https://cdn.pixabay.com/photo/2021/04/30/16/47/binance-logo-6219389_1280.png" />
                  </Avatar>
                  <div>
                    <span className="font-bold text-orange-700">
                      {"Akhil"}
                    </span>
                    <span> Tread</span>
                  </div>
                </div>
              </SheetTitle>
            </SheetHeader>
            <SideBar />
          </SheetContent>
        </Sheet>
        <p className="text-sm lg:text-base cursor-pointer">
          {"Akhil Treading"}
        </p>
        <div className="p-0 ml-9">
          <Button variant="outline" className="flex items-center gap-3">
            <MagnifyingGlassIcon />
            <span>Search</span>
          </Button>
        </div>
      </div>
      <div>
        <Avatar>
          <AvatarFallback>
            {user?.fullName?.[0]?.toUpperCase() || "?"}
          </AvatarFallback>
        </Avatar>
      </div>
    </div>
  );
}
