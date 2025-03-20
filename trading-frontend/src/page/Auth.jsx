import React from "react";
import image from "../assets/image.png";
import SignIn from "./SignIn";
import SignUp from "./SignUp";
import ForgotPassword from "./ForgotPassword";
import { Button } from "@/components/ui/button";
import { useLocation, useNavigate } from "react-router-dom";

export default function Auth() {
  console.log(image);

  const backgroundStyle = {
    backgroundImage: `url(${image})`,
    backgroundSize: "cover",
    backgroundPosition: "center",
    backgroundRepeat: "no-repeat",
  };

  const navigate = useNavigate();
  const location = useLocation();

  return (
    <div className="h-screen relative" style={backgroundStyle}>
      <div className="absolute top-0 right-0 left-0 bottom-0 bg-[#030712] bg-opacity-50">
        <div className="backdrop-blur-lg  p-6 absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 flex flex-col justify-center items-center h-[35rem] w-[30rem] rounded-md z-50 bg-black bg-opacity-50 shadow-2xl shadow-white px-10">
          <h1 className="text-6xl font-bold pb-9">Akhil Trading</h1>
          {location.pathname === "/signup" ? (
            <section className="w-full">
              <SignUp />
              <div className="flex items-center justify-center">
                <span>Already have an account?</span>
                <Button onClick={() => navigate("/signin")} variant="ghost">
                  Sign In
                </Button>
              </div>
            </section>
          ) : location.pathname === "/forgot-password" ? (
            <section className="w-full">
              <ForgotPassword />
              <div className="flex items-center justify-center mt-2">
                <span>back to login?</span>
                <Button onClick={() => navigate("/signin")} variant="ghost">
                  Sign In
                </Button>
              </div>
            </section>
          ) : (
            <section className="w-full">
              <SignIn />
              <div className="flex items-center justify-center mt-2">
                <span>Don't have an account?</span>
                <Button onClick={() => navigate("/signup")} variant="ghost">
                  Sign Up
                </Button>
              </div>

              <div className="mt-10">
                <Button className='w-full py-5' onClick={() => navigate("/forgot-password")} variant="outline">
                 ForgotPassword
                </Button>
              </div>
            </section>
          )}
        </div>
      </div>
    </div>
  );
}
