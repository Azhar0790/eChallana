package in.techpinnacle.andriod.eChallan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.techpinnacle.andriod.eChallan.R;
import in.techpinnacle.andriod.eChallan.pojos.Offence4Anriod;

/**
 * Created by Vamsy on 17-Mar-16.
 */
public class OffenceAdapter extends ArrayAdapter<Offence4Anriod> {

    private ArrayList<Offence4Anriod> items;
    Context context;
    public OffenceAdapter(Context context, int textViewResourceId, ArrayList<Offence4Anriod> items) {
        super(context, textViewResourceId, items);
        this.context=context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.history, null);
        }

        Offence4Anriod c = items.get(position);
        if (c != null) {
            ((TextView)v.findViewById(R.id.vTypeLabel)).setText(c.getTypeOfVehicle());
            ((TextView)v.findViewById(R.id.vidLabel)).setText(c.getVehicleId());
            ((TextView)v.findViewById(R.id.offenceDescLabel)).setText(c.getOffenceDesc());
            ((TextView)v.findViewById(R.id.fineLabel)).setText(String.valueOf(c.getFine()));
            ((TextView)v.findViewById(R.id.paidLabel)).setText(c.isPaid()?"PAID":"UN-PAID");
        }
        return v;
    }
}
