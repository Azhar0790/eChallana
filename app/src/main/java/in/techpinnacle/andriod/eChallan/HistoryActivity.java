package in.techpinnacle.andriod.eChallan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import in.techpinnacle.andriod.eChallan.adapters.OffenceAdapter;
import in.techpinnacle.andriod.eChallan.pojos.Offence4Anriod;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Intent i = getIntent();
        Offence4Anriod[] offences = (Offence4Anriod[]) i.getSerializableExtra("offences");
        ListView lv = (ListView)findViewById(R.id.historyList);
        lv.setAdapter(new OffenceAdapter(HistoryActivity.this,
                R.layout.history, new ArrayList<Offence4Anriod>(Arrays.asList(offences))));

    }
}
