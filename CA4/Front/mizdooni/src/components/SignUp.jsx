import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export const SignUp = () => {
  const [isManager, setIsManager] = useState(false);
  const [isCustomer, setIsCustomer] = useState(false);
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    email: "",
    country: "",
    city: "",
    role: "",
  });
  const navigate = useNavigate();

  const handleManagerChange = () => {
    setIsManager(!isManager);
    setIsCustomer(false);
    setFormData({ ...formData, role: "manager" });
  };

  const handleCustomerChange = () => {
    setIsCustomer(!isCustomer);
    setIsManager(false);
    setFormData({ ...formData, role: "client" });
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const reqBody = {
        username: formData.username,
        password: formData.password,
        address: {
          city: formData.city,
          country: formData.country,
        },
        role: formData.role,
        email: formData.email,
      };
      const response = await fetch("http://localhost:8080/users", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(reqBody),
      });
      if (response.ok) {
        console.log("Signup successful");
        localStorage.setItem("username", formData.username);
        if (formData.role === "manager") {
          navigate("/Manager-Restaurant");
        } else {
        }
        console.log("Login successful");
      } else {
        const errorMessage = await response.text();
        throw new Error(errorMessage);
      }
    } catch (error) {
      console.log(error);
    }

    setFormData({
      username: "",
      password: "",
      country: "",
      city: "",
      role: "",
    });
  };

  return (
    <div className="col-12 p-4">
      <form className="cardRadius p-4" onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="username" className="form-label">
            Username
          </label>
          <input
            type="text"
            className="form-control bg-light"
            name="username"
            value={formData.username}
            onChange={handleChange}
          />
        </div>
        <div className="mb-3">
          <label htmlFor="password" className="form-label">
            Password
          </label>
          <input
            type="password"
            className="form-control bg-light"
            name="password"
            value={formData.password}
            onChange={handleChange}
          />
        </div>
        <div className="mb-3">
          <label htmlFor="email" className="form-label">
            email
          </label>
          <input
            type="email"
            className="form-control bg-light"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
        </div>
        <div className="mb-3">
          <label htmlFor="country" className="form-label">
            Country
          </label>
          <input
            type="text"
            className="form-control bg-light"
            name="country"
            value={formData.country}
            onChange={handleChange}
          />
        </div>
        <div className="mb-3">
          <label htmlFor="city" className="form-label">
            City
          </label>
          <input
            type="text"
            className="form-control bg-light"
            name="city"
            value={formData.city}
            onChange={handleChange}
          />
        </div>
        <div className="d-flex mb-3">
          <div className="form-check me-auto">
            <input
              className="form-check-input"
              type="checkbox"
              id="flexCheckDefault"
              checked={isManager}
              onChange={handleManagerChange}
            />
            <label className="form-check-label" htmlFor="flexCheckDefault">
              Manager
            </label>
          </div>
          <div className="form-check ">
            <input
              className="form-check-input"
              type="checkbox"
              id="flexCheckChecked"
              checked={isCustomer}
              onChange={handleCustomerChange}
            />
            <label className="form-check-label" htmlFor="flexCheckChecked">
              Customer
            </label>
          </div>
        </div>

        <div className="mb-3 text-center">
          <button type="submit" className="btn btn-danger col-9 ">
            Sign Up
          </button>
        </div>
      </form>
    </div>
  );
};
