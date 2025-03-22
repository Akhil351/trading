import { Button } from "@/components/ui/button"; // Import Button component
import SignUp from "./SignUp"; // Import SignUp component
import SignIn from "./SignIn"; // Import SignIn component
import ForgotPassword from "./ForgotPassword"; // Import ForgotPassword component
import { useLocation, useNavigate } from "react-router-dom"; // React Router hooks

const Auth = () => {
  const navigate = useNavigate(); // Hook for navigation
  const location = useLocation(); // Hook to get the current path

  // Function to handle navigation
  const handleNavigation = (path) => {
    navigate(path);
  };

  return (
    <div className="h-screen relative">
      {/* Background Image */}
      <img
        src="/asset/bitcoin.jpg"
        alt="Background"
        className="absolute top-0 left-0 w-full h-full object-cover"
      />

      {/* Overlay for Dark Effect */}
      <div className="absolute inset-0 bg-black bg-opacity-50"></div>

      {/* Authentication Box */}
      <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 
                      backdrop-blur-md flex flex-col justify-center items-center 
                      h-[35rem] w-[30rem] rounded-md z-50 bg-black bg-opacity-50 
                      shadow-2xl shadow-white">
        
        {/* Title */}
        <h1 className="text-6xl font-bold pb-9 text-white">Akhil Trading</h1>

        {/* Conditional Rendering for Authentication Forms */}
        {location.pathname === "/signup" ? (
          <section className="w-full">
            <div className="w-full px-10 space-y-5">
              <SignUp /> {/* Render SignUp component */}

              {/* Navigation to SignIn */}
              <div className="flex items-center justify-center text-white">
                <span>Already have an account?</span>
                <Button onClick={() => handleNavigation("/signin")} variant="ghost">
                  Sign In
                </Button>
              </div>
            </div>
          </section>
        ) : location.pathname === "/forgot-password" ? (
          <section className="p-5 w-full">
            <ForgotPassword /> {/* Render ForgotPassword component */}

            {/* Back to SignIn */}
            <div className="flex items-center justify-center mt-5 text-white">
              <span>Back to login?</span>
              <Button onClick={() => navigate("/signin")} variant="ghost">
                Sign In
              </Button>
            </div>
          </section>
        ) : (
          <section className="w-full">
            <div className="w-full px-10 space-y-5">
              <SignIn /> {/* Render SignIn component */}

              {/* Navigation to SignUp */}
              <div className="flex items-center justify-center text-white">
                <span>Don't have an account?</span>
                <Button onClick={() => handleNavigation("/signup")} variant="ghost">
                  Sign Up
                </Button>
              </div>

              {/* Forgot Password Button */}
              <div>
                <Button
                  onClick={() => navigate("/forgot-password")}
                  variant="outline"
                  className="w-full py-5 text-white"
                >
                  Forgot Password?
                </Button>
              </div>
            </div>
          </section>
        )}
      </div>
    </div>
  );
};

export default Auth;
