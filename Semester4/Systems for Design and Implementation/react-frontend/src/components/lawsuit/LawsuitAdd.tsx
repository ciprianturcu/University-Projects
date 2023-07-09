import { Autocomplete, Button, Card, CardActions, CardContent, IconButton, InputLabel, MenuItem, Select, TextField} from "@mui/material";
import { Container } from "@mui/system";
import { useCallback, useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { BACKEND_API_URL } from "../../constants";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";
import { Lawsuit, LawsuitType } from "../../models/Lawsuit";
import { Client, ClientType } from "../../models/Client";
import { debounce } from "lodash";

export const LawsuitAdd = () => {
	const navigate = useNavigate();
	const [lawsuit, setLawsuit] = useState({
		description: "",
		type: LawsuitType['CIVIL'],
		state:"",
		court_date: "",
		// client: {
		// 	name: "",
		// 	phone_number: "",
		// 	city:"",
		// 	date_of_birth: "",
		// 	type: ClientType['PHYSICAL'],
		// },
		client:1,
	});

	const [clients, setClients] = useState<Client[]>([]);
	
	const fetchSuggestions = async (query : string) => {
		try{
			const response = await axios.get<Client[]>(`${BACKEND_API_URL}/client/autocomplete/?query=${query}`);
			const data = await response.data;
			setClients(data);
			console.log(clients)
		}
		catch (error){
			console.error("Error fetching sugestions: ", error);
		}
	};

	const debouncedFetchSuggestions = useCallback(debounce(fetchSuggestions, 500), []);

	useEffect(() => {
		return () => {
			debouncedFetchSuggestions.cancel();
		};

	}, [debouncedFetchSuggestions]);

	const addLawsuit = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		try {
			await axios.post(`${BACKEND_API_URL}/lawsuit/`, lawsuit);
			navigate("/lawsuit");
		} catch (error) {
			console.log(error);
		}
	};

	const handleInputChange = (event : any, value: any, reason: any) => {
		console.log("input", value, reason);
		if(reason == "input"){
			debouncedFetchSuggestions(value);
		}
	}

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/lawsuit`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<form onSubmit={addLawsuit}>
						<TextField
							id="description"
							label="Description"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setLawsuit({ ...lawsuit, description: event.target.value })}
						/>
						<InputLabel id="lawsuit-type-label">Lawsuit Type</InputLabel>
						<Select
							labelId="lawsuit-type-label"
							id="lawsuit-type"
							value={lawsuit.type}
							onChange={(event) => setLawsuit({...lawsuit, type: event.target.value as LawsuitType})}
						>
							<MenuItem value="Civil">Civil</MenuItem>
							<MenuItem value="Commercial">Commercial</MenuItem>
                            <MenuItem value="Criminal">Criminal</MenuItem>
                            <MenuItem value="Family">Family</MenuItem>
                            <MenuItem value="Juvenile">Juvenile</MenuItem>
                            <MenuItem value="Tax">Tax</MenuItem>
						</Select>
						<TextField
							id="state"
							label="State"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setLawsuit({ ...lawsuit, state: event.target.value })}
						/>
						<TextField
							id="court_date"
							label="Court Date"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setLawsuit({ ...lawsuit, court_date: event.target.value })}
						/>
						<Autocomplete
							id = "client"
							options = {clients}
							getOptionLabel={(option) => `${option.name} - ${option.phone_number}`}
							renderInput={(params) => <TextField {...params} label="Client" variant="outlined" required/>}
							filterOptions={(options, state) => options.filter((option) => option.name.toLowerCase().includes(state.inputValue.toLowerCase()))}
							onInputChange={handleInputChange}
							onChange={(event, value) => {
								if(value)
								{
									console.log(value);
									setLawsuit({...lawsuit, client: value.id as unknown as number})
								}
							}}
						/>

						<Button type="submit">Add Lawsuit</Button>
					</form>
				</CardContent>
				<CardActions></CardActions>
			</Card>
		</Container>
	);
};