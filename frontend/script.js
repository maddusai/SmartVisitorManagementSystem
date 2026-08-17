const API_BASE = "https://smartvisitormanagementsystem.onrender.com/api";

// === Register Employee ===
document.getElementById("registerEmpBtn").addEventListener("click", async () => {
  const name = document.getElementById("empName").value;
  const email = document.getElementById("empEmail").value;
  const department = document.getElementById("empDepartment").value;

  if (!name || !email || !department) return alert("All fields required!");

  const res = await fetch(`${API_BASE}/employees`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name, email, department })
  });

  const data = await res.json();
  alert("Employee Registered with ID: " + data.employeeId);
});

// === Get All Employees ===
document.getElementById("getAllEmpBtn").addEventListener("click", async () => {
  const res = await fetch(`${API_BASE}/employees`);
  const employees = await res.json();

  const ul = document.getElementById("employeeList");
  ul.innerHTML = "";

  employees.forEach(emp => {
    const li = document.createElement("li");
    li.textContent = `ID: ${emp.employeeId}, Name: ${emp.name}, Email: ${emp.email}`;
    ul.appendChild(li);
  });
});

// === Get Employee by ID ===
document.getElementById("getEmpByIdBtn").addEventListener("click", async () => {
  const id = document.getElementById("empIdInput").value;

  if (!id) return alert("Enter an Employee ID");

  const res = await fetch(`${API_BASE}/employees/${id}`);

  if (res.status === 404) {
    document.getElementById("singleEmployeeResult").textContent =
      "Employee not found!";
    return;
  }

  const emp = await res.json();

  document.getElementById("singleEmployeeResult").textContent =
    `ID: ${emp.employeeId}, Name: ${emp.name}, Email: ${emp.email}`;
});

// === Register Visitor ===
document.getElementById("registerVisitorBtn").addEventListener("click", async () => {
  const name = document.getElementById("visitorName").value;
  const phone = document.getElementById("visitorPhone").value;
  const email = document.getElementById("visitorEmail").value;
  const purpose = document.getElementById("visitorPurpose").value;

  if (!name || !phone || !email || !purpose)
    return alert("All fields required!");

  const res = await fetch(`${API_BASE}/visitors`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name, phone, email, purpose })
  });

  const data = await res.json();
  alert("Visitor Registered with ID: " + data.visitorId);
});

// === Get All Visitors ===
document.getElementById("getAllVisitorsBtn").addEventListener("click", async () => {
  const res = await fetch(`${API_BASE}/visitors`);
  const visitors = await res.json();

  const list = document.getElementById("visitorList");
  list.innerHTML = "";

  visitors.forEach(visitor => {
    const li = document.createElement("li");
    li.textContent =
      `ID: ${visitor.visitorId}, Name: ${visitor.name}, Phone: ${visitor.phone}, Purpose: ${visitor.purpose}`;
    list.appendChild(li);
  });
});

// === Get Visitor By ID ===
document.getElementById("getVisitorBtn").addEventListener("click", async () => {
  const id = document.getElementById("getVisitorId").value;

  if (!id) return alert("Enter Visitor ID");

  const res = await fetch(`${API_BASE}/visitors/${id}`);

  if (!res.ok) return alert("Visitor not found");

  const visitor = await res.json();

  document.getElementById("singleVisitorResult").textContent =
    `Name: ${visitor.name}, Phone: ${visitor.phone}, Purpose: ${visitor.purpose}`;
});

// === Check-In ===
document.getElementById("checkInBtn").addEventListener("click", async () => {
  const visitorId = document.getElementById("checkinVisitorId").value;
  const employeeId = document.getElementById("checkinEmployeeId").value;

  if (!visitorId || !employeeId)
    return alert("Visitor ID and Employee ID required");

  const res = await fetch(
    `${API_BASE}/visits/checkin?visitorId=${visitorId}&employeeId=${employeeId}`,
    {
      method: "POST"
    }
  );

  const data = await res.json();
  alert("Visitor Checked In! Visit ID: " + data.visitId);
});

// === Check-Out ===
document.getElementById("checkOutBtn").addEventListener("click", async () => {
  const visitId = document.getElementById("checkoutVisitId").value;

  if (!visitId) return alert("Visit ID is required");

  const res = await fetch(
    `${API_BASE}/visits/checkout?visitId=${visitId}`,
    {
      method: "POST"
    }
  );

  const data = await res.json();
  alert("Visitor Checked Out! Time: " + data.checkOutTime);
});

// === Load Visit Logs ===
document.getElementById("fetchLogsBtn").addEventListener("click", async () => {
  const res = await fetch(`${API_BASE}/visits`);
  const logs = await res.json();

  const tbody = document.getElementById("logsTableBody");
  tbody.innerHTML = "";

  logs.forEach(log => {
    const row = document.createElement("tr");

    row.innerHTML = `
      <td>${log.visitId}</td>
      <td>${log.visitorName}</td>
      <td>${log.employeeName}</td>
      <td>${log.checkInTime || "-"}</td>
      <td>${log.checkOutTime || "-"}</td>
      <td>${log.status}</td>
    `;

    tbody.appendChild(row);
  });
});