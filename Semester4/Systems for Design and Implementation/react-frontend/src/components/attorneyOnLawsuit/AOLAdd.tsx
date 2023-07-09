import { Autocomplete, Button, Card, CardActions, CardContent, IconButton, InputLabel, MenuItem, Select, TextField} from "@mui/material";
import { Container } from "@mui/system";
import { useCallback, useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { BACKEND_API_URL } from "../../constants";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";
import { Lawsuit, LawsuitType } from "../../models/Lawsuit";
import { Client, ClientType } from "../../models/Client";
import { debounce, set } from "lodash";
import { AttorenyRole, AttorneyOnLawsuit, WorkType } from "../../models/AttorneyOnLawsuit";
import { Attorney, ExperienceType, SpecializationType } from "../../models/Attorney";

export const AOLAdd = () => {
	const navigate = useNavigate();
	const [aol, setAOL] = useState({
		attorney:1,
		lawsuit:1,
		att_role: AttorenyRole['PRIMARY'],
        work_type: WorkType['EVIDENCE'],
        description: "",
	});

	const [lawsuits, setLawsuits] = useState<Lawsuit[]>([]);
    const [attorneys, setAttorneys] = useState<Attorney[]>([]);
	
	const fetchSuggestionsLawsuit = async (query : string) => {
		try{
			const response = await axios.get<Lawsuit[]>(`${BACKEND_API_URL}/lawsuit/autocomplete/?query=${query}`);
			const data = await response.data;
			setLawsuits(data);
		}
		catch (error){
			console.error("Error fetching lawsuit sugestions: ", error);
		}
	};

    const fetchSuggestionsAttorney = async (query : string) => {
		try{
			const response = await axios.get<Attorney[]>(`${BACKEND_API_URL}/attorney/autocomplete/?query=${query}`);
			const data = await response.data;
			setAttorneys(data);
		}
		catch (error){
			console.error("Error fetching attorney sugestions: ", error);
		}
	};

	const debouncedFetchSuggestionsLawsuit = useCallback(debounce(fetchSuggestionsLawsuit, 500), []);
    const debouncedFetchSuggestionsAttorney = useCallback(debounce(fetchSuggestionsAttorney, 500), []);

	useEffect(() => {
		return () => {
			debouncedFetchSuggestionsLawsuit.cancel();
		};

	}, [debouncedFetchSuggestionsLawsuit]);

    useEffect(() => {
		return () => {
			debouncedFetchSuggestionsAttorney.cancel();
		};

	}, [debouncedFetchSuggestionsAttorney]);

	const addAOL = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		try {
			await axios.post(`${BACKEND_API_URL}/aol/`, aol);
			navigate("/attorney-on-lawsuit");
		} catch (error) {
			console.log(error);
		}
	};

	const handleInputChangeLawsuit = (event : any, value: any, reason: any) => {
		console.log("input", value, reason);
		if(reason == "input"){
			debouncedFetchSuggestionsLawsuit(value);
		}
	}

    const handleInputChangeAttorney = (event : any, value: any, reason: any) => {
		console.log("input", value, reason);
		if(reason == "input"){
			debouncedFetchSuggestionsAttorney(value);
		}
	}

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/attorney-on-lawsuit`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<form onSubmit={addAOL}>
                        <Autocomplete
							id = "attorney"
							options = {attorneys}
							getOptionLabel={(option) => `${option.name} - ${option.specialization}`}
							renderInput={(params) => <TextField {...params} label="Attorney" variant="outlined" required/>}
							filterOptions={(x) => x}
							onInputChange={handleInputChangeAttorney}
							onChange={(event, value) => {
								if(value)
								{
									console.log(value);
									setAOL({...aol, attorney: value.id})
								}
							}}
						/>
                        <Autocomplete
							id = "lawsuit"
							options = {lawsuits}
							getOptionLabel={(option) => `${option.description} - ${option.type}`}
							renderInput={(params) => <TextField {...params} label="Lawsuit" variant="outlined" required/>}
							filterOptions={(x) => x}
							onInputChange={handleInputChangeLawsuit}
							onChange={(event, value) => {
								if(value)
								{
									console.log(value);
									setAOL({...aol, lawsuit: value.id})
								}
							}}
						/>
						<InputLabel id="att-role-label">Lawsuit Type</InputLabel>
						<Select
							labelId="att-role-label"
							id="att-role"
							value={aol.att_role}
							onChange={(event) => setAOL({...aol, att_role: event.target.value as AttorenyRole})}
						>
							<MenuItem value="Primary">Primary</MenuItem>
							<MenuItem value="Secondary">Secondary</MenuItem>
						</Select>
                        <InputLabel id="work-type-label">Lawsuit Type</InputLabel>
						<Select
							labelId="work-type-label"
							id="work-type"
							value={aol.work_type}
							onChange={(event) => setAOL({...aol, work_type: event.target.value as WorkType})}
						>
							<MenuItem value="Documents">Documents</MenuItem>
							<MenuItem value="Statement Preparation">Statement Preparation</MenuItem>
                            <MenuItem value="Evidence Collection">Evidence Collection</MenuItem>
						</Select>
                        <TextField
							id="description"
							label="Description"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setAOL({ ...aol, description: event.target.value })}
						/>
						<Button type="submit">Add Attorney-Lawsuit</Button>
					</form>
				</CardContent>
				<CardActions></CardActions>
			</Card>
		</Container>
	);
};