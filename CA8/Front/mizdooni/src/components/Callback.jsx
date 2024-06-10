import React, { useEffect } from "react";
import { useData } from "../Data/DataContext";
import { useNavigate } from "react-router-dom";

export const Callback = () => {
  const navigate = useNavigate();
  const { setIsLogin } = useData();
  useEffect(() => {
    const handleCallback = async () => {
      const urlParams = new URLSearchParams(window.location.search);
      const code = urlParams.get("code");
      if (code) {
        try {
          const response = await fetch("http://localhost:8093/callback", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({ code }),
          });
          const data = await response.json();
          if (response.ok) {
            localStorage.setItem("token", data.token);
            localStorage.setItem("username", data.username);
            setIsLogin(true);

            navigate("/Home");
          } else {
            const errorMessage = await response.text();
            throw new Error(errorMessage);
          }
        } catch (error) {
          console.error("Error:", error);
        }
      }
    };

    handleCallback();
  }, [navigate]);

  return <div>Loading...</div>;
};
