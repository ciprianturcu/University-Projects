import { Button, Card, CardActions, CardContent, IconButton, InputLabel, MenuItem, Select, TextField } from "@mui/material";
import { Container } from "@mui/system";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { BACKEND_API_URL } from "../../constants";
import { Client, ClientType } from "../../models/Client";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";

export const ClientAdd = () => {
	const navigate = useNavigate();
	const [client, setClient] = useState({
		name: "",
		phone_number: "",
		city:"",
		date_of_birth: "",
		type: ClientType['PHYSICAL'],
	});

	const addClient = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		try {
			await axios.post(`${BACKEND_API_URL}/client/`, client);
			navigate("/client");
		} catch (error) {
			console.log(error);
		}
	};

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/client`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<form onSubmit={addClient}>
						<TextField
							id="name"
							label="Name"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setClient({ ...client, name: event.target.value })}
						/>
						<TextField
							id="phoneNumber"
							label="Phone Number"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setClient({ ...client, phone_number: event.target.value })}
						/>
						<TextField
							id="city"
							label="City"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setClient({ ...client, city: event.target.value })}
						/>
						<TextField
							id="date_of_birth"
							label="Birthday"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setClient({ ...client, date_of_birth: event.target.value })}
						/>
						<InputLabel id="client-type-label">Client Type</InputLabel>
						<Select
							labelId="client-type-label"
							id="client-type"
							value={client.type}
							onChange={(event) => setClient({...client, type: event.target.value as ClientType})}
						>
							<MenuItem value="Juridical Person">Juridical Person</MenuItem>
							<MenuItem value="Physical Person">Physical Person</MenuItem>
						</Select>

						<Button type="submit">Add Client</Button>
					</form>
				</CardContent>
				<CardActions></CardActions>
			</Card>
		</Container>
	);
};